
import bodyParser from 'body-parser';
import express from 'express';
import cartModel from '../model/cart.model.js';

const router = express.Router();
router.use(bodyParser.urlencoded({ extended: false }))

router.get('/',async (req,res)=>{
    try{
        
        const data = {
            email: req.payload.email
        }
        console.log("EMAIL"+data.email)
        const carts = await cartModel.getCartByEmail(data.email)
        res.send({
            email:carts.email,
            arrBooks:carts.books
        })

    }catch(error){
        console.log(error)
        res.send({
            "exitcode": 500,
        });
    }
})
export default router