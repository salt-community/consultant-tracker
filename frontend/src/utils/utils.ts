export const selectColor = (type: string) => {
  switch (type) {
    case "Konsult-tid":
    case "Egen administration":
    case "VAB":
      return "#6EACDA";
    case "Semester":
      return "#7bc46e";
    case "Sjuk":
      return "#F3FEB8";
    case "Tjänstledig":
      return "#8ddfc2";
    case "Remaining Days":
      return "#a4a4a4";
    case "Föräldraledig":
      return "#000000";
    default:
      return "#EF5A6F";
  }
};
