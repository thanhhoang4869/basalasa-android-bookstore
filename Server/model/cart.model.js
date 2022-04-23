import mongoose from 'mongoose'
import config from '../config/config.js'
import accountModel from './account.model.js'
import bookModel from './book.model.js'
import cartModel from './cart.model.js'

const url = config.url

mongoose.connect(url, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
})

const CheckoutSchema = mongoose.Schema({
    email: {
        type: String
    },
    books: [{
        id: { type: Number },
        quantity: { type: Number },
        price: {type:Number}
    },],
    total: {
        type: Number
    },
    phone: { type: Number },
    address: { type: String },
    receiver: { type: String }
})

const CartSchema = mongoose.Schema({
    email: {
        type: String
    },
    books: [{
        id: { type: Number },
        quantity: { type: Number }
    },]
})

const Cart = mongoose.model('cart', CartSchema, 'cart')
const Checkout = mongoose.model('checkout', CheckoutSchema, 'checkout')

export default {
    getCartByEmail: async (email) => {
        try {
            const cart = await Cart.findOne({ email: email }).lean()
            return cart
        } catch (error) {
            console.log(error)
            return null;
        }
    },
    CreateCart: async (email) => {
        try {
            const cart = new Cart({
                email: email,
                books: []
            })
            await cart.save()
            return cart
        }
        catch (error) {
            console.log(error)
            return null;
        }
    },
    UpdateCart: async (email, id, quantity) => {
        try {
            let cart = await Cart.findOne({ email: email }).lean()
            let arrayCart = []
            let oldquantity = 0
            for (let i = 0; i < cart.books.length; i++) {
                arrayCart.push(cart.books[i])
                if (cart.books[i].id === id) {
                    oldquantity = cart.books[i].quantity
                    arrayCart[i].quantity = quantity
                }
            }
            return await Cart.findOneAndUpdate({ email, email }, { books: arrayCart })

        } catch (error) {
            console.log(error)
            return null;
        }
    },
    DeleteCart: async (email, id) => {
        try {
            let cart = await Cart.findOne({ email: email }).lean()
            let arrayCart = []
            for (let i = 0; i < cart.books.length; i++) {
                if (cart.books[i].id != id) {
                    arrayCart.push(cart.books[i])
                }
            }
            return await Cart.findOneAndUpdate({ email, email }, { books: arrayCart })
        } catch (error) {
            console.log(error)
            return null
        }
    },
    AddCart: async (email, book) => {
        try {
            let check = await Cart.findOne({ email: email }).lean()
            let temp = false
            for (let i = 0; i < check.books.length; i++) {
                if (check.books[i].id == book.id) {
                    check.books[i].quantity += book.quantity
                    temp = true
                }

            }
            let cart = null
            if (temp === false) {
                cart = await Cart.findOneAndUpdate({ email: email }, { $push: { books: book } })
                return cart
            }
            cart = await Cart.findOneAndUpdate({ email: email }, { books: check.books })
            return cart
        } catch (error) {
            console.log(error)
            return null
        }
    },
    DeleteItem: async (email, id) => {
        try {
            let check = await Cart.findOne({ email: email }).lean()
            let result = []
            for (let i = 0; i < check.books.length; i++) {
                if (check.books[i].id !== id) {
                    result.push(check.books[i])
                }
            }
            await Cart.findOneAndUpdate({ email: email }, { books: result })
        } catch (error) {
            console.log(error)
            return null
        }
    },
    createOrder: async (email, data, phone, address, receiver) => {
        try {
            const user = await accountModel.findByEmail(email)
            if (phone === "") {    
                phone = user.phone
            }
            if (address === "") {    
                address = user.address
            }
            if (receiver === "") {    
                receiver = user.email
            }
            let total = 0;
            let result = []
            for (let i = 0; i < data.length; i++) {
                total += data[i].price * data[i].quantity
                let book = await bookModel.getBookByID(data[i].id)
                if (data[i].quantity < book.quantity) {
                    console.log("hople")
                    result.push(data[i])
                    await bookModel.updateQuantity(data[i].id,(book.quantity-data[i].quantity))
                    await cartModel.DeleteItem(email,data[i].id)  
                }
                else{
                    return null
                }
            }
            total += 30000
            const newCheckout = {
                email: email,
                books: result,
                total: total,
                phone: phone,
                address: address,
                receiver: receiver
            }

            return await Checkout.create(newCheckout)


        } catch (error) {
            console.log(error)
            return null
        }
    },

}