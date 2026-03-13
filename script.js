const vertexShader = `
    varying vec2 vUv;
    void main() {
        vUv = uv;
        gl_Position = vec4(position, 1.0);
    }
`;

const fragmentShader = `
    precision highp float;
    uniform vec2 resolution;
    uniform float time;
    uniform vec3 baseColor;

    float random(vec2 st) {
        return fract(sin(dot(st.xy, vec2(12.9898,78.233))) * 43758.5453123);
    }

    void main() {
        vec2 uv = (gl_FragCoord.xy * 2.0 - resolution.xy) / min(resolution.x, resolution.y);
        
        // Efecto mosaico dinámico
        vec2 grid = vec2(4.0, 2.0);
        uv.x = floor(uv.x * 128.0 / grid.x) / (128.0 / grid.x);
        uv.y = floor(uv.y * 128.0 / grid.y) / (128.0 / grid.y);

        float t = time * 0.1 + random(vec2(uv.x)) * 0.5;
        float dist = length(uv);
        
        vec3 color = vec3(0.0);
        float line = 0.0015 / abs(fract(t - 0.5 * dist) - 0.5);
        
        // Aplicar el color temático (púrpura o verde)
        color = baseColor * line * (1.5 - dist);

        gl_FragColor = vec4(color, 1.0);
    }
`;

function createShader(containerId, colorHex) {
    const container = document.getElementById(containerId);
    if (!container) return;

    const scene = new THREE.Scene();
    const camera = new THREE.Camera();
    camera.position.z = 1;

    const geometry = new THREE.PlaneBufferGeometry(2, 2);
    const uniforms = {
        time: { value: 1.0 },
        resolution: { value: new THREE.Vector2() },
        baseColor: { value: new THREE.Color(colorHex) }
    };

    const material = new THREE.ShaderMaterial({
        uniforms,
        vertexShader,
        fragmentShader
    });

    const mesh = new THREE.Mesh(geometry, material);
    scene.add(mesh);

    const renderer = new THREE.WebGLRenderer({ antialias: true, alpha: true });
    container.appendChild(renderer.domElement);

    function resize() {
        const w = container.offsetWidth;
        const h = container.offsetHeight;
        renderer.setSize(w, h);
        uniforms.resolution.value.x = w;
        uniforms.resolution.value.y = h;
    }

    window.addEventListener('resize', resize);
    resize();

    function animate(now) {
        requestAnimationFrame(animate);
        uniforms.time.value = now * 0.001;
        renderer.render(scene, camera);
    }
    requestAnimationFrame(animate);
}

// Inicializar: Púrpura para Charlotte, Verde para Protectora
window.addEventListener('DOMContentLoaded', () => {
    createShader('shader-charlotte', '#8a2be2');
    createShader('shader-protectora', '#00ff7f');
});