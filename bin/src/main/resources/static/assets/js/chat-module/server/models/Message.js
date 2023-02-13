import mongoose from 'mongoose';
import { v4 as uuidv4 } from "uuid";

const MESSAGE_TYPES = {
    TYPE_TEXT: "text",
    TYPE_IMAGE: "image",
};

const { Schema } = mongoose;

const readByRecipientSchema = new mongoose.Schema({
    _id: false,
    readByUserId: String,
    readAt: {
        type: Date,
        default: Date.now(),
    },
}, {
    timestamps: false,
});

const messageSchema = new Schema({
    _id: {
        type: String,
        default: () => uuidv4().replace(/\-/g, ""),
    },
    roomId: String,
    message: mongoose.Schema.Types.Mixed,
    type: {
        type: String,
        default: () => MESSAGE_TYPES.TYPE_TEXT,
    },
    sentByUser: String,
    deleted: {
        type: Boolean,
        default: false,
    },
    eviction: {
        type: Boolean,
        default: false,
    },
    readByRecipients: [readByRecipientSchema],
}, {
    timestamps: true,
    collection: "chatmessages",
});



messageSchema.statics.sendMessageToRoom = async function(roomId, message, userSendId) {
    try {
        const sentMessage = await this.create({
            roomId,
            message,
            userSendId,
            readByRecipients: { readByUserId: postedByUser }
        });

        const aggregate = await this.aggregate([
            // get post where _id = post._id
            { $match: { _id: post._id } },
            // do a join on another table called users, and 
            // get me a user whose _id = postedByUser
            {
                $lookup: {
                    from: 'users',
                    localField: 'sentByUser',
                    foreignField: '_id',
                    as: 'sentByUser',
                }
            },
            { $unwind: '$sentByUser' },
            // do a join on another table called chatrooms, and 
            // get me a chatroom whose _id = chatRoomId
            {
                $lookup: {
                    from: 'chatrooms',
                    localField: 'roomId',
                    foreignField: '_id',
                    as: 'roomInfo',
                }
            },
            { $unwind: '$roomInfo' },
            { $unwind: '$roomInfo.userIds' },
            // do a join on another table called users, and 
            // get me a user whose _id = userIds
            {
                $lookup: {
                    from: 'users',
                    localField: 'roomInfo.userIds',
                    foreignField: '_id',
                    as: 'roomInfo.userProfile',
                }
            },
            { $unwind: '$roomInfo.userProfile' },
            // group data
            {
                $group: {
                    _id: '$roomInfo._id',
                    postId: { $last: '$_id' },
                    roomId: { $last: '$roomInfo._id' },
                    message: { $last: '$message' },
                    type: { $last: '$type' },
                    sentByUser: { $last: '$postedByUser' },
                    readByRecipients: { $last: '$readByRecipients' },
                    roomInfo: { $addToSet: '$roomInfo.userProfile' },
                    createdAt: { $last: '$createdAt' },
                    updatedAt: { $last: '$updatedAt' },
                    deleted: { $last: '$deleted' },
                    eviction: { $last: '$eviction' },
                }
            }
        ]);
        return aggregate;
    } catch (error) {
        throw error;
    }
};

messageSchema.statics.markMessageReaded = async function(roomId, currentUserId) {
    try {
        return this.updateMany({
            roomId: roomId,
            'readByRecipients.readByUserId': { $ne: currentUserId }
        }, {
            $addToSet: {
                readByRecipients: { readByUserId: currentUserId }
            }
        }, {
            multi: true
        });
    } catch (error) {
        throw error;
    }
}