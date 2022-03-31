import categoryModel from '../model/category.model.js';

function assignRoutes(app) {
    app.post('/category', async (req, res) => {
        console.log("dag o category")
        try {
            const ret = await categoryModel.getCategory();
            console.log(ret)
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