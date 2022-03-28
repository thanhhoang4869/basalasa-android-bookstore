import accountRoutes from './account.route.js'

function assignRoutes(app) {
    accountRoutes.assignRoutes(app)
}

export default {
    assignRoutes
}