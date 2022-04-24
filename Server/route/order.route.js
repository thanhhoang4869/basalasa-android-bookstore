
import bodyParser from 'body-parser';
import express from 'express';
import cartModel from '../model/cart.model.js';
import bookModel from '../model/book.model.js';
import orderModel from '../model/order.model.js';

const router = express.Router();
router.use(bodyParser.urlencoded({ extended: false }))

router.get('/',async (req,res)=>{
    
})
router.post('/update',async(req,res)=>{
    
})

router.post('/delete',async (req,res)=>{
    
})
router.post('/add',async (req,res)=>{
    try{
        const data = {
            email: req.payload.email
        }
        let {address,arrBooks,email,phone} = req.body
        const cart = await orderModel.createOrder(data.email,arrBooks,phone,address,email)
        if(!cart){
            res.send({
                "exitcode":500
            })
        }
         else{
            res.send({
                "exitcode":1
            })
        }
        
    }catch(error){
        console.log(error)
        res.send({
            "exitcode":500
        })
    }
})
router.post('/checkout',async(req,res)=>{
    try{
        const data = {
            email: req.payload.email
        }
        let {address,arrBooks,email,phone} = req.body
        const cart = await cartModel.createOrder(data.email,arrBooks,phone,address,email)
        if(!cart){
            res.send({
                "exitcode":500
            })
        }
         else{
            res.send({
                "exitcode":1
            })
        }
        
    }catch(error){
        console.log(error)
        res.send({
            "exitcode":500
        })
    }  
})

export default router