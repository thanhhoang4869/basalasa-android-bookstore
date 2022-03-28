import mongoose from 'mongoose'
import config from '../config/config.js'
const url = config.url

const connection = mongoose.connect(url, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
})

export default mongoose

// function connect() {
//     return new Promise((resolve, reject) => {
//         mongoose.connect(url, {
//             useNewUrlParser: true,
//             useUnifiedTopology: true,
//         }).then(() => {
//             conn = mongoose.connection;
//             console.log(conn.name);
//             resolve()
//             return;
//         }).catch(err => {
//             console.log(`Error creating MongoDB connection ${err}`);
//             reject();
//         })
//     })
// }