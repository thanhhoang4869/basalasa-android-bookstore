import accountRoutes from '../route/account.route.js';
import categoryRoutes from '../route/category.route.js';
import bookRoutes from '../route/book.route.js';
import searchRoutes from '../route/search.route.js';

export default function (app) {
  app.use('/account', accountRoutes);
  app.use('/category', categoryRoutes);
  app.use('/book', bookRoutes);
  app.use('/search', searchRoutes);
}
