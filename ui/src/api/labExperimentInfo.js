import request from '@/utils/request'

export function getLaboratoryInfo(data) {
  return request({
    url: '/laboratory/lab',
    method: 'get',
    params: { data }
  })
}
export function add(data) {
  return request({
    url: '/laboratory/lab',
    method: 'post',
    data
  })
}
export function updateLabExperimentInfo(data) {
  return request({
    url: '/laboratory/lab',
    method: 'put',
    data
  })
}

export function deleteLabExperimentInfo(data) {
  return request({
    url: '/laboratory/lab',
    method: 'delete',
    data
  })
}
