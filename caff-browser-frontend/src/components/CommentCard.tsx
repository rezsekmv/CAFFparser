import { faEdit, faTrashAlt } from '@fortawesome/free-regular-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { CommentDto, CommentService, ImageService } from '../services/openapi';

interface CommentCardProps {
  content: string;
  id: number;
  modifiable: boolean;
  username: string;
  imageId: number;
}

const CommentCard = (props: CommentCardProps) => {
  const navigate = useNavigate();

  const hadleDelete = () => {
    CommentService.deleteComment(props.id)
      .then((res) => {
        navigate('/');
      })
      .catch((err) => {
        console.log('Can not delete comment');
      });
  };

  const handleEdit = () => {
    CommentService.updateComment(props.id, {content: props.content, imageId: props.imageId})
      .then((res) => {
        navigate('/');
      })
      .catch((err) => {
        console.log('Can not update comment');
      });
  };

  return (
    <div style={styles.commentCard}>
      <div style={styles.commentData}>
        <div style={styles.username}>{props.username}</div>
        <div>
          {props.modifiable && (
            <div>
              <span className="btn btn-warning">
                <FontAwesomeIcon icon={faEdit}></FontAwesomeIcon>
              </span>{' '}
              <span className="btn btn-danger">
                <FontAwesomeIcon icon={faTrashAlt}></FontAwesomeIcon>
              </span>
            </div>
          )}
        </div>
      </div>
      <div>{props.content}</div>
    </div>
  );
};

const styles = {
  commentCard: {
    boxShadow: '0 2px 1px 1px rgba(0, 0, 0, 0.1)',
    borderRadius: '5px',
    padding: '20px',
    marginBottom: 15,
  },
  commentData: {
    display: 'flex',
    justifyContent: 'space-between',
  },
  username: {
    color: 'grey',
  },
};

export default CommentCard;
