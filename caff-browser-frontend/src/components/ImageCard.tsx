import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faComment, faUser } from '@fortawesome/free-regular-svg-icons';

const ImageCard = (props: any) => {
  return (
    <div className="card">
      <div className="card-image-container">
        <a href={'image/' + props.id}>
          <img
            className="card-img-top custom-card-img"
            src={props.imageUrl}
            alt=""
          />
        </a>
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
              <div className="comment-count counter-child">test</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ImageCard;
