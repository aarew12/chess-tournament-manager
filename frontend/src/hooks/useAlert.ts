import { useState, useCallback } from 'react';

export interface AlertState {
  type: 'error' | 'success' | 'warning' | 'info';
  message: string;
  id: number;
}

export const useAlert = () => {
  const [alerts, setAlerts] = useState<AlertState[]>([]);

  const showAlert = useCallback((type: AlertState['type'], message: string) => {
    const id = Date.now();
    setAlerts(prev => [...prev, { type, message, id }]);
  }, []);

  const hideAlert = useCallback((id: number) => {
    setAlerts(prev => prev.filter(alert => alert.id !== id));
  }, []);

  const showError = useCallback((message: string) => showAlert('error', message), [showAlert]);
  const showSuccess = useCallback((message: string) => showAlert('success', message), [showAlert]);
  const showWarning = useCallback((message: string) => showAlert('warning', message), [showAlert]);
  const showInfo = useCallback((message: string) => showAlert('info', message), [showAlert]);

  return {
    alerts,
    showAlert,
    hideAlert,
    showError,
    showSuccess,
    showWarning,
    showInfo,
  };
};