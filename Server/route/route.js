import accountRoutes from './account.route.js'
import categoryRoutes from './category.route.js'
import bookRoutes from './book.route.js'

function assignRoutes(app) {
    accountRoutes.assignRoutes(app)
    categoryRoutes.assignRoutes(app)
    bookRoutes.assignRoutes(app)
}

export default {
    assignRoutes
}