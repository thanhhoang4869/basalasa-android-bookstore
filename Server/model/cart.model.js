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
        name:{type:String},
        price:{type:Number},
        img:{type:String},
        quantity:{type:Number}
    },]
})

const Cart = mongoose.model('cart', CartSchema, 'cart')

export default {
    getCartByEmail:async (email)=>{
        console.log("LOOOOOO")
        try{
            const  cart = await Cart.findOne({email:email}).lean()
            console.log(cart)
            return cart
        }catch(error){
            console.log(error)
            return null;
        }
    }
}