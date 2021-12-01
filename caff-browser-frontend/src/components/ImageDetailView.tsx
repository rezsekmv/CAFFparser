import { faSmileBeam } from '@fortawesome/free-regular-svg-icons';
import { Component } from 'react';
import { useParams } from 'react-router-dom';
import { ImageDto, CommentDto } from '../services/openapi';
import CommentCard from './CommentCard';

const ImageDetailView = () => {

    const { id } = useParams();

    const data: ImageDto = {
        commentable: true,
        comments: [
            {
                content: 'This is a comment',
                id: 1,
                imageId: 1,
                modifiable: true,
                userDisplayName: 'Test Commenter'
            },
            {
                content: 'This is a comment, too',
                id: 1,
                imageId: 1,
                modifiable: false,
                userDisplayName: 'Test User'
            }
        ],
        commentsSize: 1,
        gifPath: 'https://i0.wp.com/www.printmag.com/wp-content/uploads/2021/02/4cbe8d_f1ed2800a49649848102c68fc5a66e53mv2.gif?fit=476%2C280&ssl=1',
        id: 1,
        modifiable: true,
        userDisplayName: 'Test User'
    }

    return (
        <div>
            <img style={styles.mainImage} src="https://i0.wp.com/www.printmag.com/wp-content/uploads/2021/02/4cbe8d_f1ed2800a49649848102c68fc5a66e53mv2.gif?fit=476%2C280&ssl=1" />
            <div style={styles.infoBar}>
                <div>
                    {data.userDisplayName}
                </div>
            </div>
            <div style={styles.commentsSection}>
                {data.comments?.map(comment =>
                    <CommentCard
                        content={comment.content}
                        id={comment.id}
                        modifiable={comment.modifiable}
                        username={comment.userDisplayName}
                    />
                )}
            </div>
        </div>
    );
}

const styles = {
    mainImage: {
        display: 'block',
        margin: 'auto',
        'max-width': 400,
        'max-height': 400
    },
    infoBar: {
        maxWidth: 400,
        margin: 'auto',
        height: 40,
    },
    commentsSection: {
        width: '400px',
        display: 'block',
        margin: 'auto'
    }
};

export default ImageDetailView;