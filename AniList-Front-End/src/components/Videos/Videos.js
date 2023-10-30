import React, { useEffect, useState } from 'react';

const videos = [
  {
    id: "oKPHqaOC50s",
  },
  {
    id: "CYcrmsdZuyw",
  },
  {
    id: "Kjta12rmtkA",
  },
  {
    id: "S8_YwFLCh4U",
  },
  {
    id: "PPk1zmbOnow",
  },
];

export const Videos = () => {
  const [current, setCurrent] = useState(0);

  useEffect(() => {
    renderCurrentVideo(videos[current].id);
  }, [current]);

  const renderCurrentVideo = (id) => {
    const currentContainer = document.querySelector("#current");
    currentContainer.innerHTML = `<iframe width="50%" height="720" src="https://www.youtube.com/embed/${id}" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>`;
  };

  const handleNextClick = () => {
    setCurrent((prev) => (prev + 1 < videos.length ? prev + 1 : prev));
  };

  const handlePrevClick = () => {
    setCurrent((prev) => (prev - 1 >= 0 ? prev - 1 : prev));
  };

  return (
    <section id="videos">
      <div id="slider">
        <div id="controls">
          <button id="prev" onClick={handlePrevClick}>
            <span className="material-icons">&#xe5e0;</span>
          </button>
          <button id="next" onClick={handleNextClick}>
            <span className="material-icons">&#xe5e1;</span>
          </button>
        </div>
      </div>
      <div id="current"></div>
      <div id="videos-container">
        {videos.map((video, index) => (
          <div className="item" key={index}>
            <a href="#" onClick={(e) => { e.preventDefault(); setCurrent(index); }}>
            </a>
          </div>
        ))}
      </div>
    </section>
  );
};
