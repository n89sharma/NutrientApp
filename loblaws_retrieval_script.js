const axios = require('axios')
const fs = require('fs');


function get_all_cereal_data(file_path) {

    let get_all_items_url = 'https://www.instacart.ca/v3/retailers/311/module_data/aisle_11642_61501'
    let get_item_url = 'https://www.instacart.ca/v3/containers/items/'
    let cookie = 'remember_user_token=W1s0NTI2MDY2Nl0sIiQyYSQxMCRSaERrNHl1aEhHUGxLb09KdVpxU00uIiwiMTU0NjczMjQyMi44MzgyMjU0Il0%3D--9e90ab073ad8d18dbe91ba0221ef6837ffc0275f'
    let pages = [1, 2, 3]
    let elements_per_page = 100
    let aisle_id = 61501
    let aisle_title = 'Cereal'
    let promises = []

    pages.forEach(page => {
        promises.push(get_items(get_all_items_url, get_item_url, page, elements_per_page, aisle_id, aisle_title, cookie));
    });
    axios.all(promises)
        .then(results => {
            let all_data = [].concat.apply([], results);
            fs.writeFile(file_path, JSON.stringify(all_data), function (err) {
                if (err) {
                    console.log('writing error', err);
                }
            });
        })
        .catch(() => console.log('result object'));
};

function get_items(get_items_url, get_item_url, page, elements_per_page, aisle_id, aisle_title, cookie) {

    return axios.get(get_items_url, {
        params: {
            aisle_title: aisle_title,
            aisle_id: aisle_id,
            page: page,
            per: elements_per_page
        },
        headers: {
            Cookie: cookie
        }
    })
        .then(response => {
            let promises = [];
            let nutrient_data = [];
            response.data.module_data.items.forEach(item => {
                setTimeout(function(){}, 35);
                console.log(item.id);
                promises.push(get_nutrients(get_item_url, item, cookie));
            });
            return axios.all(promises)
                .then(results => {
                    results.forEach(result => nutrient_data.push(result));
                    return nutrient_data;
                })
                .catch(() => {
                    return {
                        error: 'error'
                    }
                });
        })
        .catch(error => console.log(JSON.stringify(error)));
}

function get_nutrients(get_item_url, element, cookie) {
    return axios.get(get_item_url + element.id, {
        headers: {
            Cookie: cookie
        }
    })
        .then(response => {
            let modules = response.data.container.modules
            let module = modules.find(module => {
                return module.id === "item_details_attributes_66a1c26c89401b9e69f6e2c224c1ce1f"
            });
            if (!!module) {
                let nutrition = module.data.nutrition
                return {
                    id: element.id,
                    name: element.name,
                    size: element.size,
                    serving_size: nutrition == null ? null : nutrition.serving_size ,
                    calories: nutrition == null ? null : nutrition.calories,
                    nutrients: nutrition == null ? null : nutrition.nutrients
                };
            } else {
                return {
                    error: {
                        id: element.id,
                        message: 'error - module not found',
                        module: module,
                        modules: modules
                    }
                };
            }
        })
        .catch(() => {
            return {
                error: {
                    id: element.id,
                    message: 'error - in nutrient retrieval'
                }
            };
        });
}

function read_file(file_path) {
    fs.readFile(file_path, 'utf8', (err, data) => {
        data = JSON.parse(data)
        console.log(data.length)
        blanks = data.filter(element => {
            return !element.nutrients
        })
        blanks.forEach(element => console.log(element.id))
        console.log(blanks.length)
    })
}


let get_item_url = 'https://www.instacart.ca/v3/containers/items/'
let cookie = 'remember_user_token=W1s0NTI2MDY2Nl0sIiQyYSQxMCRSaERrNHl1aEhHUGxLb09KdVpxU00uIiwiMTU0NjczMjQyMi44MzgyMjU0Il0%3D--9e90ab073ad8d18dbe91ba0221ef6837ffc0275f'
file_path = 'cereal3.json'
get_all_cereal_data(file_path);
// read_file(file_path);

let item = {
    id: 'item_465430072',
    name: '',
    size: ''
}
// get_nutrients(get_item_url, item, cookie).then(response => {
//     console.log(response);
// })