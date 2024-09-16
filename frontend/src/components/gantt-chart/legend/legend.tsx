import { legend, redDaysAndWeekends } from '../../../utils/utils';
import './legend.css'

const Legend = () => {
  return (
    <section className="legend">
      <div>
        <h4>Time chunks</h4>
        <div className="legend__container">
          {legend.map((el,index) => {
            return <div className="legend-item__wrapper" key={index}>
            <div className="legend__color-indicator" style={{background: el.color}}></div>
              <p className="legend__description">{el.description}</p>
            </div>
          })}
        </div>
      </div>
      <div>
        <h4>Red days and weekends</h4>
        <div className="legend__container">
          {redDaysAndWeekends.map((el, index) => {
            return <div className="legend-item__wrapper" key={index}>
              <div className="legend__color-indicator" style={{background: el.color}}></div>
              <p className="legend__description">{el.description}</p>
            </div>
          })}
        </div>
      </div>
    </section>
  );
};

export default Legend;