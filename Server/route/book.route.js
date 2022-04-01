import bookModel from '../model/book.model.js';
import dotenv from 'dotenv';

function assignRoutes(app) {
    app.get('/book', async (req, res) => {
        try {
            const ret = await categoryModel.getCategory();
            res.send(ret);
        } catch (err) {
            console.log(err)
            res.send({
                "exitcode": 500,
            });
        }
    });
}

export default {
    assignRoutes
}