const account = require('../controller/account')

function assignRoutes(app) {
    app.get('/account/login', account.returnData);
}

module.exports = {
    assignRoutes
}