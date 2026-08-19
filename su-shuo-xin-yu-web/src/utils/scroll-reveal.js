export function useScrollReveal() {
  let observer = null

  function init() {
    if (typeof window === 'undefined') return

    observer = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          entry.target.classList.add('visible')
        }
      })
    }, {
      threshold: 0.1,
      rootMargin: '0px 0px -30px 0px'
    })

    // Observe all scroll-reveal elements AND immediately reveal those already visible
    const els = document.querySelectorAll('.scroll-reveal')
    els.forEach(el => {
      observer.observe(el)
      // Immediately check if already in viewport
      const rect = el.getBoundingClientRect()
      if (rect.top < window.innerHeight && rect.bottom > 0) {
        el.classList.add('visible')
      }
    })

    // Fallback: after 1s, reveal all remaining (catches any timing issues)
    setTimeout(() => {
      document.querySelectorAll('.scroll-reveal:not(.visible)').forEach(el => {
        el.classList.add('visible')
      })
    }, 1000)
  }

  function cleanup() {
    if (observer) {
      observer.disconnect()
      observer = null
    }
  }

  return { init, cleanup }
}

export function recordBehavior(type, targetId, targetType, extraData) {
  // 纯静态站无后端，不发送埋点请求（避免 405 报错刷屏）
}
