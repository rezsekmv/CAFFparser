import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faComment, faUser } from '@fortawesome/free-regular-svg-icons';
import { Link } from 'react-router-dom';

const ImageCard = (props: any) => {
  return (
    <div className="card" style={{height: '270px'}}>
      <div className="card-image-container">
        <Link to={'image/' + props.id}>
          <img
            className="card-img-top custom-card-img"
            src={props.imageUrl}
            alt=""
          />
        </Link>
      </div>

      <div className="card-body">
        <h5>{props.title}</h5>
        <div className="row img-property-row">
          <div className="col-6 card-comment">
            <div className="comment-icon counter-child">
              <FontAwesomeIcon icon={faComment}></FontAwesomeIcon>
            </div>
            <div className="comment-count counter-child">
              {props.commentCount}
            </div>
          </div>
          <div className="col-6">
            <div className="card-user">
              <div className="comment-icon counter-child">
                <FontAwesomeIcon icon={faUser}></FontAwesomeIcon>
              </div>
              <div className="comment-count counter-child">{props.user}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ImageCard;
