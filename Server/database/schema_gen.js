//only to store schema, not to run

const mongoose = require('mongoose')
const config = require('../config/config')
const url = config.url

function connect() {
    return new Promise((resolve, reject) => {
        mongoose.connect(url, {
            useNewUrlParser: true,
            useUnifiedTopology: true,
        }).then(() => {
            conn = mongoose.connection;
            console.log(conn.name);
            resolve()
            return;
        }).catch(err => {
            console.log(err);
            reject();
        })
    })
}

connect().then(() => {
    // conn.db.listCollections().toArray(dropTable)
    UserSchema = new mongoose.Schema({
        password: { type: String, require: true },
        email: { type: String, require: true, unique: true },
        address: String,
        phone: String,
        role: Number,
        fullName: String,
        status: Boolean,
        emailToken: String,
    })

    User = mongoose.model('user', UserSchema, 'user')

    BookSchema = new mongoose.Schema({
        id: { type: Number, required: true, min: 0, unique: true },
        name: { type: String, required: true },
        author: { type: String, required: true },
        distributor: { type: String, required: true },
        seller: { type: String, required: true },
        price: { type: Number, required: true },
        saleprice: Number,
        category: { type: String, required: true },
        picture: { type: String, require: true },
        release_year: Date,
        description: { type: String, required: true },
        quantity: { type: Number, required: true, min: 1 },
        state: Boolean,
        star: Number,
        comments: [{
            userEmail: { type: String },
            rating: { type: Number },
            review: { type: String }
        },]
    })
    Book = mongoose.model('book', BookSchema, 'book')

    CartSchema = new mongoose.Schema({
        email: { type: String, required: true },
        book_id: { type: mongoose.Types.ObjectId, required: true },
        release_year: Number,
    })
    Cart = mongoose.model('cart', CartSchema, 'cart')

    OrderSchema = new mongoose.Schema({
        email: {
            type: String
        },
        books: [{
            id: { type: Number },
            quantity: { type: Number },
            price: {type:Number}
        },],
        status:{type:String},
        date:{type:Date},
        total: {
            type: Number
        },
        phone: { type: Number },
        address: { type: String },
        receiver: { type: String },
    
    })
    Order = mongoose.model('order', OrderSchema, 'order')

    CategorySchema = new mongoose.Schema({
        name: { type: String, required: true },
        image: { type: String },
    })
    Category = mongoose.model('category', CategorySchema, 'category')

}).catch(err => {
    console.log(err)
})

function dropTable(err, names) {
    if (err) {
        console.log(err);
    } else {
        for (i = 0; i < names.length; i++) {
            conn.db.dropCollection(names[i].name);
        }
    }
}