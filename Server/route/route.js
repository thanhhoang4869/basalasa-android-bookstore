import accountRoutes from './account.route.js'
import categoryRoutes from './category.route.js'
import bookRoutes from './book.route.js'

export default function (app) {
    app.use('/account', accountRoutes);
    app.use('/category', categoryRoutes);
    app.use('/book', bookRoutes);
}