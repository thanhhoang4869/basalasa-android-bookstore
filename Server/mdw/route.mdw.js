import accountRoutes from '../route/account.route.js'
import categoryRoutes from '../route/category.route.js'
import bookRoutes from '../route/book.route.js'

export default function(app) {
    app.use('/account', accountRoutes);
    app.use('/category', categoryRoutes);
    app.use('/book', bookRoutes);
}