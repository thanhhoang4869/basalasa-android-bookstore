import categoryModel from '../model/category.model.js';

function assignRoutes(app) {
    app.post('/category', async (req, res) => {
        try {
            const ret = await categoryModel.getCategory();
            res.send({ "arrCategory": ret });
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