// Comment.jsx
import React from 'react';
import { ChildenComment } from './ChildenComment';

export const Comment = ({ comment, depth = 0 }) => {
  console.log("Comment");
  console.log(comment);


  const calculateTimeAgo = (createdDate) => {
    const currentDate = new Date();
    const createdDateObj = new Date(createdDate);
    console.log(createdDateObj);
    const timeDifference = currentDate - createdDateObj;
    const seconds = Math.floor(timeDifference / 1000);
    const minutes = Math.floor(seconds / 60);
    const hours = Math.floor(minutes / 60);
    const days = Math.floor(hours / 24);
    const years = Math.floor(days / 365);

    if (years > 0) {
      return `replied ${years} ${years === 1 ? 'year' : 'years'} ago`;
    } else if (days > 0) {
      return `replied ${days} ${days === 1 ? 'day' : 'days'} ago`;
    } else if (hours > 0) {
      return `replied ${hours} ${hours === 1 ? 'hour' : 'hours'} ago`;
    } else if (minutes > 0) {
      return `replied ${minutes} ${minutes === 1 ? 'minute' : 'minutes'} ago`;
    } else {
      return `replied ${seconds} ${seconds === 1 ? 'second' : 'seconds'} ago`;
    }
  };
  return (
    <>
      <div className={`comment`}>
        <div className='header_comment'>
            <a href="/" className='user'>
                <div className='avatar' style={{
                      backgroundImage: `url(${comment.imagen !== null ? comment.imagen : '../../../images/profile/profile.png'})`,
                    }}    
                />{comment.nickname}
            </a>
            <div className='info'>
                <p className='time'>{calculateTimeAgo(comment.fechaCreacion)}  </p>
            </div>
        </div>
        <div className='markdown_comment'>
      
        </div>
        {comment.contenido}
      </div>
      {comment.subRespuestaIds && comment.subRespuestaIds.length > 0 && (
        <div className='children'>
          <ChildenComment comment_id={comment.id} depth={depth + 1} />
        </div>
      )}
    </>
  );
}
