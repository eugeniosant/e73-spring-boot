import './public-path';
import React from 'react';
import { createRoot } from 'react-dom/client';
import App from '../App';

const ATTRIBUTES = {
    config: 'config'
};

class WidgetElement extends HTMLElement {

    static get observedAttributes() {
        return Object.values(ATTRIBUTES);
    }

    connectedCallback() {
        this.mountPoint = document.createElement('div');
        this.appendChild(this.mountPoint);

        this.root = createRoot(this.mountPoint);
        this.render();
    }

    attributeChangedCallback(attribute, oldValue, newValue) {
        if (!WidgetElement.observedAttributes.includes(attribute)) {
            throw new Error(`Untracked changed attributes: ${attribute}`)
        }
        if (this.mountPoint && newValue !== oldValue) {
            this.render();
        }
    }

    render() {
        const attributeConfig = this.getAttribute(ATTRIBUTES.config);
        const config = attributeConfig && JSON.parse(attributeConfig);

        this.root.render(
            <App config={config} />
        );
    }
}

customElements.define('simple-mfe', WidgetElement);
