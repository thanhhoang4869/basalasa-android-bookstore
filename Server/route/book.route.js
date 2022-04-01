import bookModel from '../model/book.model.js';
import bodyParser from "body-parser";
import express from 'express'
import route from './route.js';

const router = express.Router();
router.use(bodyParser.urlencoded({ extended: false }))

router.get('/onsale', async (req, res) => {
    const ret = await bookModel.getBookOnSale();
    console.log(ret)
    res.send({ arrBookOnSale: ret || null });

router.get('/',async(req,res)=>{
    try{
        console.log("TEST")
        const ret = await bookModel.findAll();
        console.log(ret);
        res.send({arrBook:ret});
    }catch(error){
        console.log(error)
        res.send({
            "exitcode":500,
        });
    }
    
});
router.get('/:bookdID', async (req, res) => {
    try {
        const ret = await bookModel.getBook(req.params.bookdID);
        res.send({
            "id": ret.id,
            "name": ret.name,
            "author": ret.author,
            "distributor": ret.distributor,
            "seller": ret.seller,
            "price": ret.price,
            "saleprice": ret.saleprice,
            "category": ret.category,
            "picture": ret.picture,
            "release_year": ret.release_year,
            "description": ret.description,
            "quantity": ret.quantity,
            "state": ret.state,
            "star": ret.star,
            "comments":ret.comments
        });
    } catch (err) {
        console.log(err)
        res.send({
            "exitcode": 500,
        });
    }

});


export default router