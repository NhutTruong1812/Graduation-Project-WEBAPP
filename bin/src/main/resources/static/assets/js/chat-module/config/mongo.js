import mongoose from 'mongoose'
import config from './index.js'

const CONNECTION_URL = 'mongodb+srv://paul:0311nh0909tbht@cluster0.nuclidk.mongodb.net/?retryWrites=true&w=majority'; //`mongodb://${config.db.url}/${config.db.name}`

mongoose.connect(CONNECTION_URL, {
    useNewUrlParser: true,
    useUnifiedTopology: true
})

mongoose.connection.on('connected', () => {
    console.log('Mongo has connected succesfully')
})
mongoose.connection.on('reconnected', () => {
    console.log('Mongo has reconnected')
})
mongoose.connection.on('error', error => {
    console.log('Mongo connection has an error', error)
    mongoose.disconnect()
})
mongoose.connection.on('disconnected', () => {
    console.log('Mongo connection is disconnected')
})