import request from '@/utils/request'

export function getLocation(data) {
  return request({
    url: '/laboratory/location',
    method: 'get',
    params: { data }
  })
}
export function getLocationPage(data) {
  return request({
    url: '/laboratory/location/page',
    method: 'get',
    params: { data }
  })
}
export function newLocation(data) {
  return request({
    url: '/laboratory/location',
    method: 'post',
    data
  })
}
export function updateLocation(data) {
  return request({
    url: '/laboratory/location',
    method: 'put',
    data
  })
}

export function deleteLocation(data) {
  return request({
    url: '/laboratory/location',
    method: 'delete',
    data
  })
}
