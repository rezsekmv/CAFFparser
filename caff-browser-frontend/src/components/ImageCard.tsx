import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faComment, faUser } from '@fortawesome/free-regular-svg-icons';

const ImageCard = (props: any) => {
    return (
        <div className="card">
            <img className="card-img-top custom-card-img" src={props.imageUrl} alt="" />
            <div className="card-body">
                <h5 className="card-title"><a className="url-no-format" href={"image/" + props.id}>{props.title}</a></h5>
                <div className="row img-property-row">
                    <div className="col-6 card-comment">
                        <div className="comment-icon counter-child"><FontAwesomeIcon icon={faComment}></FontAwesomeIcon></div>
                        <div className="comment-count counter-child">{props.commentCount}</div>
                    </div>
                    <div className="col-6">
                        <div className="card-user">
                            <div className="comment-icon counter-child"><FontAwesomeIcon icon={faUser}></FontAwesomeIcon></div>
                            <div className="comment-count counter-child">test</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ImageCard;