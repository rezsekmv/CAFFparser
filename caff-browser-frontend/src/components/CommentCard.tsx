import { Card } from "react-bootstrap";

const CommentCard = (props: any) => {

    return (
        <div style={styles.commentCard}>
            <div style={styles.commentData}>
                <div style={styles.username}>
                    {props.username}
                </div>
                <div>
                    {props.modifiable ? <div>Edit</div> : ''}
                </div>
            </div>
            <div>{props.content}</div>
        </div>
    );
}

const styles = {
    commentCard: {
        boxShadow: '0 2px 1px 1px rgba(0, 0, 0, 0.1)',
        borderRadius: '5px',
        padding: '20px',
        marginBottom: 15
    },
    commentData: {
        display: 'flex',
        justifyContent: 'space-between'
    },
    username: {
        color: 'grey'
    }
};

export default CommentCard;