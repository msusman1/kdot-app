config.resolve = {
    fallback: {
        crypto: false,
        fs: false,
        path: false,
        stream: false,
        buffer: false,
        os: false,
        url: false,
    }
};
config.resolve.preferRelative = true;
const CopyPlugin = require("copy-webpack-plugin");
config.plugins.push(
    new CopyPlugin({
        patterns: [
         { from: "../../node_modules/@matrix-org/olm/olm.wasm", to: "."},
        ],

    })
)
