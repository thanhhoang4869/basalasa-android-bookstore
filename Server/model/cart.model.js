import mongoose from 'mongoose'
import config from '../config/config.js'


const url = config.url

mongoose.connect(url, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
})


const CartSchema = mongoose.Schema({
    email: {
        type: String
    },
    books: [{
        _id: { type: String },
        quantity: { type: Number }
    },]
})

const Cart = mongoose.model('cart', CartSchema, 'cart')


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
    UpdateCart: async (email, _id, quantity) => {
        try {
            let cart = await Cart.findOne({ email: email }).lean()
            let arrayCart = []
            let oldquantity = 0
            for (let i = 0; i < cart.books.length; i++) {
                arrayCart.push(cart.books[i])
                if (cart.books[i]._id === _id) {
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
    DeleteCart: async (email, _id) => {
        try {
            let cart = await Cart.findOne({ email: email }).lean()
            let arrayCart = []
            for (let i = 0; i < cart.books.length; i++) {
                if (cart.books[i]._id != _id) {
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
                if (check.books[i]._id == book._id) {
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
    DeleteItem: async (email, _id) => {
        try {
            let check = await Cart.findOne({ email: email }).lean()
            let result = []
            for (let i = 0; i < check.books.length; i++) {
                if (check.books[i]._id !== _id) {
                    result.push(check.books[i])
                }
            }
            await Cart.findOneAndUpdate({ email: email }, { books: result })
        } catch (error) {
            console.log(error)
            return null
        }
    },
    

}