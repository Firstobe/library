import request from '@/utils/request'

export function getClasses(data) {
  return request({
    url: '/laboratory/classes',
    method: 'get',
    params: { data }
  })
}
export function newClasses(data) {
  return request({
    url: '/laboratory/classes',
    method: 'post',
    data
  })
}
export function updateClasses(data) {
  return request({
    url: '/laboratory/classes',
    method: 'put',
    data
  })
}

export function deleteClasses(data) {
  return request({
    url: '/laboratory/classes',
    method: 'delete',
    data
  })
}
