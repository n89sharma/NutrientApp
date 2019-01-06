const axios = require('axios')
const fs = require('fs');

function get_items(page, elements_per_page, aisle_id, aisle_title) {
    return axios.get('https://www.instacart.ca/v3/retailers/311/module_data/aisle_11642_61501', {
        params: {
            aisle_title: aisle_title,
            aisle_id: aisle_id,
            page: page,
            per: elements_per_page
        },
        headers: {
            Cookie: "remember_user_token=W1s0NTI2MDY2Nl0sIiQyYSQxMCRSaERrNHl1aEhHUGxLb09KdVpxU00uIiwiMTU0NjczMjQyMi44MzgyMjU0Il0%3D--9e90ab073ad8d18dbe91ba0221ef6837ffc0275f"
        }
    })
        .then(response => {
            console.log('here', page);
            promises = []
            nutrient_data = []
            response.data.module_data.items.forEach(element => {
                promises.push(
                    get_nutrients(element.id).then(response => {
                        nutrient_data.push({
                            id: element.id,
                            name: element.name,
                            size: element.size,
                            serving_size: response.serving_size,
                            calories: response.calories,
                            nutrients: response.nutrients
                        });
                    }))
            });
            return Promise.all(promises).then(() => {
                return nutrient_data;
            })
        })
        .catch(error => {
            return new Promise(function (resolve, reject) {
                resolve({ 'error': 'call failed' });
            });
        });
}

function get_nutrients(elementId) {
    return axios.get('https://www.instacart.ca/v3/containers/items/' + elementId, {
        headers: {
            Cookie: "remember_user_token=W1s0NTI2MDY2Nl0sIiQyYSQxMCRSaERrNHl1aEhHUGxLb09KdVpxU00uIiwiMTU0NjczMjQyMi44MzgyMjU0Il0%3D--9e90ab073ad8d18dbe91ba0221ef6837ffc0275f"
        }
    })
        .then(response => {
            module = response.data.container.modules.find(element => {
                return element.id == "item_details_attributes_66a1c26c89401b9e69f6e2c224c1ce1f"
            });
            if (module) {
                nutrition = module.data.nutrition
                nutrients = nutrition.nutrients
                return new Promise(function (resolve, reject) {
                    resolve({
                        serving_size: nutrition.serving_size,
                        calories: nutrition.calories,
                        nutrients: nutrients
                    })
                });
            } else {
                return new Promise(function (resolve, reject) {
                    resolve({ 'error': 'no module found' })
                })
            }
        })
        .catch(error => {
            return new Promise(function (resolve, reject) {
                resolve({ 'error': 'call 2 failed' })
            })
        })
}

function get_all_cereal_data() {
    all_data = []
    pages = [1,2,3]
    elements_per_page = 100
    aisle_id = 61501
    aisle_title = 'Cereal'

    promises = []
    pages.forEach(page => {

        promises.push(
            get_items(page, elements_per_page, aisle_id, aisle_title)
                .then(response => {
                    all_data.push(response)
                }))
    })
    Promise.all(promises).then(() => {
        fs.writeFile('cereal.json', JSON.stringify(all_data), function (err) {
            if (err) {
                console.log(err);
            }
        });
    })
};

function read_file() {
    fs.readFile('cereal.json', 'utf8', (err, data) => {
        data = JSON.parse(data)
        console.log(data[0].length)
        console.log(data[1].length)
        console.log(data[2].length)
    })
}

// get_all_cereal_data();
read_file();