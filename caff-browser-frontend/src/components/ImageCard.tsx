import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faComment,
  faUser,
  IconDefinition,
} from '@fortawesome/free-regular-svg-icons';
import { Link } from 'react-router-dom';
import { faDownload } from '@fortawesome/fontawesome-free-solid';
import StaticService from '../services/StaticService';

var FileSaver = require('file-saver');

const ImageCard = (props: any) => {
  const downloadIcon = faDownload as IconDefinition;

  const downloadImage = () => {
    FileSaver.saveAs(
      StaticService.getImage(props.caffUrl),
      StaticService.getDownloadName(props.caffUrl)
    );
  };

  return (
    <div className="card" style={{ height: '250px' }}>
      <div className="card-image-container">
        <Link to={'image/' + props.id}>
          <img
            className="card-img-top custom-card-img"
            src={StaticService.getImage(props.gifUrl)}
            alt=""
          />
        </Link>
      </div>

      <div className="card-body">
        <h5>{props.title}</h5>
        <div className="row img-property-row">
          <div className="col-12">
            <div className="card-user">
              <div className="comment-icon counter-child">
                <FontAwesomeIcon icon={faUser}></FontAwesomeIcon>
              </div>
              <div className="comment-count counter-child">{props.user}</div>
            </div>
          </div>

          <div className="col-6 card-comment">
            <div className="comment-icon counter-child">
              <FontAwesomeIcon icon={faComment}></FontAwesomeIcon>
            </div>
            <div className="comment-count counter-child">
              {props.commentCount}
            </div>
          </div>
          <div className="col-6 card-comment">
            <div onClick={() => downloadImage()} className="download-child">
              <FontAwesomeIcon icon={downloadIcon}></FontAwesomeIcon>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ImageCard;
