var expect = require('expect')

// https://facebook.github.io/jest/docs/en/next/expect.html

describe('API', function() {

  it('resolves', function() {
    return expect(Promise.resolve('pitter')).resolves.toBe('pitter')
  })

  it('rejects', function() {
    return expect(Promise.reject('error')).rejects.toBe('error')
  })

  it('toBe', function() {
    
    // number
    expect(2).toBe(2)
    // string
    expect('hello expect').toBe('hello expect')
    // null
    expect(null).toBe(null)
    // undefined
    expect(undefined).toBe(undefined)

    // boolean
    expect(true).toBe(true)
    expect(false).toBe(false)

  })

  it('not', function() {

    expect("2").not.toBe(2)

    expect(null).not.toBe(undefined)

  })

  it('toBeNull', function() {

    expect(2).not.toBeNull()
    expect("2").not.toBeNull()
    expect(true).not.toBeNull()
    expect(false).not.toBeNull()
    expect(null).toBeNull()
    expect(undefined).not.toBeNull()

  })

  it('toBeDefined', function() {

    expect(2).toBeDefined()
    expect("2").toBeDefined()
    expect(true).toBeDefined()
    expect(false).toBeDefined()
    expect(null).toBeDefined()
    expect(undefined).not.toBeDefined()

  })

  it('toBeUndefined', function() {

    expect(2).not.toBeUndefined()
    expect("2").not.toBeUndefined()
    expect(true).not.toBeUndefined()
    expect(false).not.toBeUndefined()
    expect(null).not.toBeUndefined()
    expect(undefined).toBeUndefined()

  })

  it('toBeTruthy', function() {

    expect(2).toBeTruthy()
    expect("2").toBeTruthy()
    expect(true).toBeTruthy()
    expect(false).not.toBeTruthy()
    expect(null).not.toBeTruthy()
    expect(undefined).not.toBeTruthy()

  })

  it('toBeGreaterThan', function() {

    expect(5).toBeGreaterThan(3)
    expect(5).not.toBeGreaterThan(6)
    expect(5).not.toBeGreaterThan(5)

  })

  it('toBeGreaterThanOrEqual', function() {

    expect(5).toBeGreaterThanOrEqual(3)
    expect(5).toBeGreaterThanOrEqual(5)
    expect(5).not.toBeGreaterThanOrEqual(6)

  })

  it('toBeLessThan', function() {

    expect(5).toBeLessThan(6)
    expect(5).not.toBeLessThan(3)
    expect(5).not.toBeLessThan(5)

  })

  it('toBeLessThanOrEqual', function() {

    expect(5).toBeLessThanOrEqual(6)
    expect(5).toBeLessThanOrEqual(5)
    expect(5).not.toBeLessThanOrEqual(3)

  })

  it('toEqual', function() {

    expect(2).toEqual(2)
    expect(2).not.toEqual("2")
    expect("2").not.toEqual(2)
    expect("2").toEqual("2")
    expect(true).toEqual(true)
    expect(true).not.toEqual(false)
    expect(null).toEqual(null)
    expect(null).not.toEqual(undefined)
    expect(undefined).toEqual(undefined)
    expect(undefined).not.toEqual(null)
    expect({"username": "jack"}).toEqual({"username": "jack"})
    expect([{"username": "jack"}, {"username": "pitter", "age": 18}]).toEqual([{"username": "jack"}, {"username": "pitter", "age": 18}])

  })

  it('toContain', function() {

    expect('hello expect').toContain('pe')

  })

  it('toContainEqual', function() {

    expect([{"username": "jack"}, {"username": "pitter", "age": 18}]).toContainEqual({"username": "jack"})

  })

  it('toHaveLength', function() {

    expect('hello expect').toHaveLength(12)
    expect([{"username": "jack"}, {"username": "pitter", "age": 18}]).toHaveLength(2)

  })

  it('toMatch', function() {

    expect('hello expect').toMatch('hello expect')
    expect('hello expect').toMatch(/he.*pect/)
    expect('hello expect').toMatch(/^he.*pect$/)
    expect('hello expect').toMatch(new RegExp("he.*pect"))
    expect('hello expect').toMatch(new RegExp("^he.*pect$"))

  })

  it('toHaveProperty', function() {

    expect({"username": "jack"}).toHaveProperty("username")
    expect({"username": "jack"}).toHaveProperty("username", "jack")
    expect({"username": "jack"}).not.toHaveProperty("username", "pitter")
    expect({"username": "jack"}).not.toHaveProperty("age")
    expect({"username": "jack"}).not.toHaveProperty("age", 18)
  })



})