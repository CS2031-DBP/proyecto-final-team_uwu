import React, { useRef, useState } from 'react';

export const Overview = () => {
  const [showButtons, setShowButtons] = useState(false);
  const textareaRef = useRef(null);
  const [menuVisibleFilter, setMenuVisible] = useState(false);

  const toggleMenu = () => {
    setMenuVisible(!menuVisibleFilter);
  };

  const handleTextareaInput = () => {
    const textarea = textareaRef.current;
    console.log(textarea.scrollHeight);
    textarea.style.height = `auto`; // Establecer la altura según el contenido
    textarea.style.height = `${textarea.scrollHeight}px`; // Establecer la altura según el contenido
  };
  const handleTextareaClick = () => {
    setShowButtons(true);
  };

  const handleCancelClick = () => {
    setShowButtons(false);
    // Borrar el contenido del textarea
    if (textareaRef.current) {
      textareaRef.current.value = '';
      textareaRef.current.style.height = '40px  '; // Restablecer la altura
    }
  };

  const handlePublishClick = () => {
    // Agrega lógica para procesar la publicación aquí
    setShowButtons(false);
     // Borrar el contenido del textarea
     if (textareaRef.current) {
      textareaRef.current.value = '';
      textareaRef.current.style.height = '40px'; // Restablecer la altura
    }
  };

  return (
    <div className='overview'>
      <div className='section'>
        <div className='aboutme'>
          <div className='content_about'>
            <p>hola </p>
          </div>
        </div>
      </div>
      <div className='section'>
        <div className='activity'>
          <h2 className='section_header'>
            Activity
            <div className='dropdown'>
              <div className='selected' onClick={toggleMenu}>
                <span className='filter'>Filter▾</span>
              </div>
           
                <ul className={`menu_filter ${menuVisibleFilter ? 'active' : ''}`}>
                  <li>All</li>
                  <li>Status</li>
                  <li>Messages</li>
                </ul>
     
              </div>
          </h2>

          <div className='activity_edit'>
            <div className='markdown_editor'></div>
            <div className='input textarea'>
            <textarea
            autoComplete='off'
            style={{ minHeight: '40px', height: '40px' }}
            className='textarea__inner'
            placeholder='What are you thinking?'
            onClick={handleTextareaClick}
            onInput={handleTextareaInput}
            rows={1}
            ref={textareaRef}
          ></textarea>
            </div>
            {showButtons && (
              <div className='actions'>
                <div className='button cancel' onClick={handleCancelClick}>Cancel</div>
                <div className='button save' onClick={handlePublishClick}>Publish</div>
              </div>
            )}
          </div>
     
        </div>
      </div>
    </div>
  );
};
