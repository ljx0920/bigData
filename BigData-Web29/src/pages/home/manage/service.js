import fetch from '@/config/fetch'
export const queryData = (data) => fetch('/configure/byPage', data, "POST");
export const create = (data) => fetch('/configure/creating', data, "POST",true);
export const edit = (data) => fetch('/configure/updating', data, "POST",true);
export const deleting = (data) => fetch('/configure/deleting', data, "GET",true);
export const byCondition = (data) => fetch('/configure/byCondition', data, "POST");