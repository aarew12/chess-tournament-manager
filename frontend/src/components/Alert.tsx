import React, { useEffect } from 'react';

interface Props {
  type: 'error' | 'success' | 'warning' | 'info';
  message: string;
  onClose: () => void;
  autoClose?: boolean;
  duration?: number;
}

export const Alert: React.FC<Props> = ({
  type,
  message,
  onClose,
  autoClose = true,
  duration = 5000
}) => {
  useEffect(() => {
    if (autoClose) {
      const timer = setTimeout(onClose, duration);
      return () => clearTimeout(timer);
    }
  }, [autoClose, duration, onClose]);

  const getStyles = () => {
    const baseStyles = {
      position: 'fixed' as const,
      top: '20px',
      right: '20px',
      padding: '15px 20px',
      borderRadius: '8px',
      boxShadow: '0 4px 12px rgba(0,0,0,0.15)',
      zIndex: 1000,
      maxWidth: '400px',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'space-between',
      animation: 'slideIn 0.3s ease-out',
    };

    const typeStyles = {
      error: {
        backgroundColor: '#fee',
        color: '#c53030',
        border: '1px solid #fed7d7',
      },
      success: {
        backgroundColor: '#f0fff4',
        color: '#38a169',
        border: '1px solid #c6f6d5',
      },
      warning: {
        backgroundColor: '#fffbf0',
        color: '#d69e2e',
        border: '1px solid #faf089',
      },
      info: {
        backgroundColor: '#ebf8ff',
        color: '#3182ce',
        border: '1px solid #bee3f8',
      },
    };

    return { ...baseStyles, ...typeStyles[type] };
  };

  const getIcon = () => {
    switch (type) {
      case 'error': return '❌';
      case 'success': return '✅';
      case 'warning': return '⚠️';
      case 'info': return 'ℹ️';
    }
  };

  return (
    <>
      <style>
        {`
          @keyframes slideIn {
            from {
              transform: translateX(100%);
              opacity: 0;
            }
            to {
              transform: translateX(0);
              opacity: 1;
            }
          }
        `}
      </style>
      <div style={getStyles()}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
          <span style={{ fontSize: '16px' }}>{getIcon()}</span>
          <span style={{ fontWeight: '500' }}>{message}</span>
        </div>
        <button
          onClick={onClose}
          style={{
            background: 'none',
            border: 'none',
            fontSize: '18px',
            cursor: 'pointer',
            marginLeft: '10px',
            opacity: 0.7,
          }}
        >
          ×
        </button>
      </div>
    </>
  );
};