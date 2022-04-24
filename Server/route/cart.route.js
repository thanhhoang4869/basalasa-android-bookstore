
import bodyParser from 'body-parser';
import express from 'express';
import cartModel from '../model/cart.model.js';
import bookModel from '../model/book.model.js';

const router = express.Router();
router.use(bodyParser.urlencoded({ extended: false }))

router.get('/',async (req,res)=>{
    try{
        const data = {
            email: req.payload.email
        }
        const carts = await cartModel.getCartByEmail(data.email)
        for (let i = 0;i<carts.books.length;i++){  
            const book =await bookModel.getBook(carts.books[i]._id)
            carts.books[i].name=book.name
            if(book.saleprice!=null)
                carts.books[i].price=book.saleprice
            else
                carts.books[i].price=book.price
            carts.books[i].img=book.picture
            carts.books[i].seller=book.seller
        }
        console.log(carts)
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
        const {_id,quantity} = req.body
        const cart = await cartModel.UpdateCart(data.email,_id,quantity)
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
        const {_id} = req.body
        const cart = await cartModel.DeleteCart(data.email,_id)
        res.send({

        })
    }catch(error){
        console.log(error)
        res.send({
            "exitcode": 500,
        });
    }
})
router.post('/add',async (req,res)=>{
    try{
        const data={
            email:req.payload.email
        }
        const {_id,quantity} = req.body
        console.log(_id)
        const book = {
            _id:_id,
            quantity: quantity
        }
        const cart = await cartModel.AddCart(data.email,book)
        res.send({
            
        })

    }catch(error){
        console.log(error)
        res.send({
            "exitcode":500
        })
    }
})

export default router