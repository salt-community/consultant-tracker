/** @type {import('next').NextConfig} */
const nextConfig = {

};

  export default {
    ...nextConfig,
    publicRuntimeConfig: {
      BACKEND_URL: process.env.NEXT_PUBLIC_BACKEND_URL,
    },
  };
