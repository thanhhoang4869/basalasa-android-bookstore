import mongoose from 'mongoose'
import config from '../config/config.js'

const url = config.url

mongoose.connect(url, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
})

const CartSchema = mongoose.Schema({
    email:{
        type:String
    },
    books:[{
        id:{type:Number},       
        quantity:{type:Number}
    },]
})

const Cart = mongoose.model('cart', CartSchema, 'cart')

export default {
    getCartByEmail:async (email)=>{
        try{
            const  cart = await Cart.findOne({email:email}).lean()
            return cart
        }catch(error){
            console.log(error)
            return null;
        }
    },
    UpdateCart:async(email,name,quantity)=>{
        try{
            let cart = await Cart.findOne({email:email}).lean()
            let arrayCart =[]
            let oldquantity =0
            for (let i =0;i<cart.books.length;i++){
                arrayCart.push(cart.books[i])
                if(cart.books[i].name===name){
                    oldquantity=cart.books[i].quantity
                    arrayCart[i].quantity = quantity
                }
            }
            return await Cart.findOneAndUpdate({email,email},{books:arrayCart})
            
        }catch(error){
            console.log(error)
            return null;
        }
    },
    DeleteCart:async(email,name)=>{
        try{
            let cart = await Cart.findOne({email:email}).lean()
            let arrayCart =[]
            for (let i =0;i<cart.books.length;i++){      
                if(cart.books[i].name!==name){
                    arrayCart.push(cart.books[i])
                }
            }
            return await Cart.findOneAndUpdate({email,email},{books:arrayCart})
        }catch(error){
            console.log(error)
            return null
        }
    },
    AddCart:async(email,book)=>{
        try{
            let check = await Cart.findOne({email:email}).lean()
            let temp = false
            for(let i =0;i<check.books.length;i++){

                console.log(check.books[i].id)
                console.log(book.id)
                if(check.books[i].id==book.id){
                    console.log("HI")
                    check.books[i].quantity+=book.quantity
                    temp =true
                }
                
            }
            console.log(temp)
            let cart= null
            if(temp===false)
                {cart = await Cart.findOneAndUpdate({email:email},{$push:{books:book}})
                return cart}
            cart = await Cart.findOneAndUpdate({email:email},{books:check.books})
            return cart
        }catch(error){
            console.log(error)
            return null
        }
    }
}