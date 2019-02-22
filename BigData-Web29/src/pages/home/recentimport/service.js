import fetch from '@/config/fetch'
export const getRecentItem = (data) => fetch('/upLoadFileItem/getRecentItem', data, "POST");