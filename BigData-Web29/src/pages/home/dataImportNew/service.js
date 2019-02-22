import fetch from '@/config/fetch'
import { baseUrl } from '@/config/env'
export const getTree = () => fetch('upLoadTree/getUploadTree',{},"GET");