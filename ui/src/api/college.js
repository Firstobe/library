import request from '@/utils/request'

export function getCollege(data) {
  return request({
    url: '/laboratory/college',
    method: 'get',
    params: { data }
  })
}
export function newCollege(data) {
  return request({
    url: '/laboratory/college',
    method: 'post',
    data
  })
}
export function updateCollege(data) {
  return request({
    url: '/laboratory/college',
    method: 'put',
    data
  })
}

export function deleteCollege(data) {
  return request({
    url: '/laboratory/college',
    method: 'delete',
    data
  })
}
