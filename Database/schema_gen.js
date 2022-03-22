const mongoose = require('mongoose')
var db = null

function connect() {
    return new Promise((resolve, reject) => {
        mongoose.connect("mongodb+srv://basalasa:mobiledev@basalasa.wcann.mongodb.net/basalasa?retryWrites=true&w=majority", {
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
    conn.db.listCollections().toArray(dropTable)
    UserSchema = new mongoose.Schema({
        username: { type: String, require: true, unique: true },
        password: { type: String, require: true },
        email: { type: String, require: true },
        otp: Number,
        dob: Date,
        address: String,
        phone: String,
        role: Number
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
        release_year: Date,
        description: { type: String, required: true },
        quantity: { type: Number, required: true, min: 1 },
        state: Boolean,
        star: Number
    })
    Book = mongoose.model('book', BookSchema, 'book')

    CartSchema = new mongoose.Schema({
        username: { type: String, required: true },
        book_id: { type: mongoose.Types.ObjectId, required: true },
        release_year: Number,
    })
    Cart = mongoose.model('cart', CartSchema, 'cart')

    OrderSchema = new mongoose.Schema({
        username: { type: String, required: true },
        product: { type: Object, required: true }
    })
    Order = mongoose.model('order', OrderSchema, 'order')

    CategorySchema = new mongoose.Schema({
        name: { type: String, required: true }
    })
    Category = mongoose.model('category', CategorySchema, 'category')

    ImageSchema = new mongoose.Schema({
        book_id: { type: mongoose.Types.ObjectId, required: true },
        index: { type: Number, required: true },
        link: { type: String, require: true }
    })
    ImageLink = mongoose.model('image', ImageSchema, 'image')
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