
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
router.post('/update',async(req,res)=>{
    try{
        const data = {
            email: req.payload.email
        }
        const {name,price,img,quantity} = req.body
        const cart = await cartModel.UpdateCart(data.email,name,quantity)
        res.send({

        })
    }catch(error){
        console.log(error)
        res.send({
            "exitcode": 500,
        });
    }
})

router.post('/delete',async (req,res)=>{
    try{
        const data = {
            email: req.payload.email
        }
        const {name} = req.body
        const cart = await cartModel.DeleteCart(data.email,name)
        res.send({

        })
    }catch(error){
        console.log(error)
        res.send({
            "exitcode": 500,
        });
    }
})
export default router