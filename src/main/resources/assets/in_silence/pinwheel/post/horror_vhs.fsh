#version 150

uniform sampler2D DiffuseSampler;
uniform float Time;
uniform float Intensity;

in vec2 texCoord;
out vec4 fragColor;

// Pseudo-Random Funktion für VHS Noise
float random(vec2 co) {
    return fract(sin(dot(co.xy, vec2(12.9898, 78.233))) * 43758.5453);
}

// CRT Barrel Distortion (Bildschirm-Wölbung)
vec2 barrelDistortion(vec2 coord, float amount) {
    vec2 cc = coord - 0.5;
    float dist = dot(cc, cc) * amount;
    return coord + cc * (1.0 + dist) * dist;
}

void main() {
    // CRT Wölbung anwenden (subtil)
    float distortionAmount = 0.15 * Intensity;
    vec2 distortedCoord = barrelDistortion(texCoord, distortionAmount);

    // Außerhalb des Bildschirms = schwarz
    if (distortedCoord.x < 0.0 || distortedCoord.x > 1.0 ||
    distortedCoord.y < 0.0 || distortedCoord.y > 1.0) {
        fragColor = vec4(0.0, 0.0, 0.0, 1.0);
        return;
    }

    // Chromatische Aberration (Farbverschiebung an den Rändern)
    float aberrationAmount = 0.002 * Intensity;
    vec2 aberrationOffset = (distortedCoord - 0.5) * aberrationAmount;

    float r = texture(DiffuseSampler, distortedCoord - aberrationOffset).r;
    float g = texture(DiffuseSampler, distortedCoord).g;
    float b = texture(DiffuseSampler, distortedCoord + aberrationOffset).b;

    vec3 color = vec3(r, g, b);

    // Subtiles VHS Rauschen
    float noise = random(distortedCoord + Time * 0.1) * 0.03 * Intensity;
    color += noise;

    // Vignette (dunkle Ecken)
    vec2 vignetteCoord = texCoord - 0.5;
    float vignette = 1.0 - dot(vignetteCoord, vignetteCoord) * 1.5;
    vignette = pow(vignette, 0.8);
    color *= mix(1.0, vignette, Intensity * 0.7);

    fragColor = vec4(color, 1.0);
}