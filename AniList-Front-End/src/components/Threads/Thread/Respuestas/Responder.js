import React, { useState } from 'react';

export const Responder = ({ hiloId, onRespuestaSubmit }) => {
  const [respuesta, setRespuesta] = useState('');

  const handleRespuestaChange = (e) => {
    setRespuesta(e.target.value);
  };

  const handleRespuestaSubmit = () => {
    // Verifica que la respuesta no esté vacía antes de enviarla al backend
    if (respuesta.trim() !== '') {
      onRespuestaSubmit(hiloId, respuesta);
      setRespuesta(''); // Limpia el campo de respuesta después de enviar
    }
  };

  return (
    <div>
      <textarea
        value={respuesta}
        onChange={handleRespuestaChange}
        placeholder="Escribe tu respuesta..."
      ></textarea>
      <button onClick={handleRespuestaSubmit}>Responder</button>
    </div>
  );
};
