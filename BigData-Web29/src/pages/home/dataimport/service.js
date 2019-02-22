import fetch from '@/config/fetch'
import { baseUrl } from '@/config/env'
export const getTree = () => fetch('/upLoadTree/getUploadTree', {}, "GET");
export const treeCreate = (data) => fetch('/upLoadTree/creating', data, "POST", true);
export const treeCreateNew = (data) => fetch('/upLoadTree/creatTree', data, "POST", true);
export const treeEdit = (data) => fetch('/upLoadTree/updating', data, "POST", true);
export const treeDelete = (data) => fetch('/upLoadTree/deletingUploadTree', data, "POST", true);
export const queryData = (data) => fetch('/upLoadFileItem/byPage', data, "POST");
export const dataDelete = (data) => fetch('/upLoadFileItem/deleting', data, "GET");
export const queryTypeData = (data) => fetch('/configure/byAll', data, "GET");
export const uploadUrl = baseUrl + '/upLoadFileItem/uploadFile';
export const downLoadUrl = baseUrl + '/upLoadFileItem/downloadFile';
export const execute = (data) => fetch('/upLoadFileItem/analysisFile', data, "POST");
export const getStatus = (data) => fetch('/upLoadFileItem/getStatus', data, "POST");